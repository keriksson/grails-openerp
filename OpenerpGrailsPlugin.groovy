import grails.spring.*
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator

class OpenerpGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.6 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Kristofer Eriksson"
    def authorEmail = "kristofer.eriksson@openfellas.com"
    def title = "Openerp plugin"
    def description = '''\\
The plugin creates a openerp client...
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/openerp"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
        openerpClientSessionAutoProxyCreator(AnnotationAwareAspectJAutoProxyCreator) {
            proxyTargetClass = true
        }

        openerpClientSessionAspect(ClientSessionAspect) {
        }
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
