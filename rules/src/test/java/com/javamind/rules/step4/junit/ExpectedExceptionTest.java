package com.javamind.rules.step4.junit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

/**
 * Le but de ce test est de montrer l'utilsation de la Rule {@link org.junit.rules.ExpectedException} permettant de controler
 * le contenu du message d'une erreur. TestNg fournit en  standard cette fonctionnalité dans les propriétés de l'annotation de
 * {@link org.testng.annotations.Test} mais pour Junit on ne peut vérifier que la nature de l'execepton via
 * {@link org.junit.Test#expected()}
 */
public class ExpectedExceptionTest {

    /**
     * Permet à un test de continuer même si une error a ete detectee pour les reporter en bloc a la fin
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void shouldVerifyExceptonMessage() throws  Exception{
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("persistence error");
        throw new RuntimeException("Pb hibernate a persistence error occured");
    }

}
