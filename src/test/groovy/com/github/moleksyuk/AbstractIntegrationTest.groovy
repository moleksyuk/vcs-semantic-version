package com.github.moleksyuk

import org.junit.BeforeClass

import java.util.zip.ZipInputStream

class AbstractIntegrationTest {

    private static final String PATH_TO_REPOSITORIES = "build/tmp"

    static final String ACCUREV_REPOSITORY_PATH = PATH_TO_REPOSITORIES + "/accurev-repository"
    static final String GIT_REPOSITORY_PATH = PATH_TO_REPOSITORIES + "/git-repository"
    static final String MERCURIAL_REPOSITORY_PATH = PATH_TO_REPOSITORIES + "/mercurial-repository"
    static final String SVN_REPOSITORY_PATH = PATH_TO_REPOSITORIES + "/svn-repository"
    static final String UNKNOWN_REPOSITORY_PATH = PATH_TO_REPOSITORIES + "/unknown-repository"

    @BeforeClass
    public static void beforeClass() throws Exception {
        File.metaClass.unzip = { String destination ->
            def destinationFile = new File(destination)
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
            }

            def result = new ZipInputStream(new FileInputStream(delegate))
            result.withStream {
                def entry
                while (entry = result.nextEntry) {
                    if (!entry.isDirectory()) {
                        new File(destination + File.separator + entry.name).parentFile?.mkdirs()
                        def output = new FileOutputStream(destination + File.separator + entry.name)
                        output.withStream {
                            int len = 0;
                            byte[] buffer = new byte[4096]
                            while ((len = result.read(buffer)) > 0) {
                                output.write(buffer, 0, len);
                            }
                        }
                    } else {
                        new File(destination + File.separator + entry.name).mkdir()
                    }
                }
            }
        }

        new File(AbstractIntegrationTest.class.getClassLoader().getResource("repositories").getFile()).eachFile {
            it.unzip(PATH_TO_REPOSITORIES)
        }
    }

}
