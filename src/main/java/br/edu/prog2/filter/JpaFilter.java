package br.edu.prog2.filter;

import br.edu.prog2.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JpaFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(JpaFilter.class.getName());
    private static final String PERSISTENCE_UNIT = "GeradorPU";

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            LOGGER.info("Unidade de persistencia '" + PERSISTENCE_UNIT + "' inicializada com sucesso.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Falha ao inicializar a unidade de persistencia.", ex);
            throw new ServletException("Nao foi possivel inicializar o JPA.", ex);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JpaUtil.setEntityManager(entityManager);

        try {
            entityManager.getTransaction().begin();
            chain.doFilter(request, response);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            LOGGER.log(Level.SEVERE, "Erro durante o processamento da requisicao.", ex);
            tratarExcecao(request, response, ex);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
            JpaUtil.clear();
        }
    }

    private void tratarExcecao(ServletRequest request, ServletResponse response, Exception ex)
            throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String mensagem = ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado.";
        httpRequest.getSession(true).setAttribute("mensagemErro", mensagem);

        if (!httpResponse.isCommitted()) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/erro.xhtml");
        }
    }

    @Override
    public void destroy() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            LOGGER.info("Unidade de persistencia finalizada.");
        }
    }
}
