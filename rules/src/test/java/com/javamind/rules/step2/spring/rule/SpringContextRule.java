package com.javamind.rules.step2.spring.rule;

import org.junit.rules.ExternalResource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Cette rule permet d'injecter un contexte Spring à une classe à tester
 */
public class SpringContextRule extends ExternalResource{

    /** Fichiers de conf spring */
    private final Class[] configs;

    /** Classe de test sur laquelle on va lancer l'instrumentation */
    private final Object classToAutowire;

    /** Contexte Spring */
    private final AnnotationConfigApplicationContext context;

    public SpringContextRule(Object classToAutowire, Class ... configs) {
        this.classToAutowire = classToAutowire;
        this.configs = configs;
        this.context = new AnnotationConfigApplicationContext();
    }


    @Override
    protected void before() throws Throwable {
        context.register(configs);
        context.refresh();
        context.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
        context.start();
    }

    @Override
    protected void after() {
        context.close();
    }

    public ApplicationContext getContext() {
        return context;
    }
}
