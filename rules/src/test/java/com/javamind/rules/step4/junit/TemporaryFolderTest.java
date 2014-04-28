package com.javamind.rules.step4.junit;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Le but de ce test est de montrer l'utilsation de la Rule {@link org.junit.rules.TemporaryFolder} permettant de créer
 * un fichier ou un répertoire dans le cadre d'un test et de le supprimer à la fin
 */
public class TemporaryFolderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldCreateFile() throws  Exception{
        File myFile = temporaryFolder.newFile("test.txt");
        assertThat(myFile).exists().isFile();
    }


    @Test
    public void shouldCreateFolder() throws  Exception{
        File myDirectory = temporaryFolder.newFolder("test");
        assertThat(myDirectory).exists().isDirectory();
    }
}
