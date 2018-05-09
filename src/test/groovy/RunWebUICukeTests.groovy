import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber.class)
@CucumberOptions(plugin = ['pretty', 'junit:build/cucumber.xml', 'json:build/cucumber.json', 'html:target/cucumber-html-report'],
        glue = ['src/test/groovy/pages', 'src/test/groovy/steps', 'classpath:io.jdev.geb.cucumber.steps.groovy.en'],
        features = ['src/test/resources/features']
)
class RunWebUICukeTests {
}