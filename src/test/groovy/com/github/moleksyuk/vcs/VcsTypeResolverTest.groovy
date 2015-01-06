package com.github.moleksyuk.vcs

import org.junit.BeforeClass
import org.junit.Test

import java.util.zip.ZipInputStream

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

class VcsTypeResolverTest {

    private static final String PATH_TO_REPOSITORIES = "build/tmp"

    private static final String GIT_REPOSITORY_PATH = PATH_TO_REPOSITORIES + "/git-repository"
    private static final String UNKNOWN_REPOSITORY_PATH = PATH_TO_REPOSITORIES + "/unknown-repository"

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

        new File(VcsTypeResolverTest.class.getClassLoader().getResource("repositories").getFile()).eachFile {
            it.unzip(PATH_TO_REPOSITORIES)
        }
    }

    @Test(expected = RuntimeException.class)
    public void testResolveUnknownVcsType() throws Exception {
        // GIVEN
        VcsTypeResolver vcsTypeDynamicResolver = new VcsTypeResolver(new File(UNKNOWN_REPOSITORY_PATH))

        // WHEN
        vcsTypeDynamicResolver.resolveVcsType()

        // THEN
    }

    @Test
    public void testResolveGitVcsType() throws Exception {
        // GIVEN
        VcsTypeResolver vcsTypeDynamicResolver = new VcsTypeResolver(new File(GIT_REPOSITORY_PATH))

        // WHEN
        VcsType actual = vcsTypeDynamicResolver.resolveVcsType()

        // THEN
        assertThat(actual, equalTo(VcsType.GIT))
    }
}