package com.javamind.rules.step4.junit;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TemporaryFolder;

import java.io.File;

/**
 * Le but de ce test est de montrer l'utilsation de la Rule {@link org.junit.rules.ErrorCollector} permettant de créer
 * un fichier ou un répertoire dans le cadre d'un test et de le supprimer à la fin
 */
public class ErrorCollectorTest {

    /**
     * Permet à un test de continuer même si une error a ete detectee pour les reporter en bloc a la fin
     */
    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();


    @Test
    public void shouldStackError() throws  Exception{
        //On empile les messages d'erreurs qui seront déclenchés à la sortie de la méthode. Pour vérifier le comportement
        //changer le false en true
        if(false) {
            errorCollector.addError(new RuntimeException("first thing went wrong"));
            errorCollector.addError(new RuntimeException("second thing went wrong"));
        }
    }

}
