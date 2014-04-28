package com.javamind.rules.step4.junit;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;

/**
 * Le but de ce test est de montrer l'utilsation de la Rule {@link org.junit.rules.TestWatcher} permettant de s'interfacer
 * dans le cycle de vie de vos tests
 */
public class TestWatcherTest {


    @Rule
    public TestWatcher testWatcher = new TestWatcher(){
        @Override
        protected void finished(Description description) {
            super.finished(description);
        }

        @Override
        protected void starting(Description description) {
            super.starting(description);
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            super.skipped(e, description);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            super.failed(e, description);
            System.out.println("erreur detectee");
        }

        @Override
        protected void succeeded(Description description) {
            super.succeeded(description);
            System.out.println("pas d'erreur detectee");
        }

        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }
    };

    @Test
    public void shouldWriteMessageSucceedInLog() throws  Exception{
        //test passant vérifier dans la sortie d'erreur que vous avez bien le message attendu
    }


}
