package org.grails.plugins.openerp.client

import grails.test.*
import grails.spring.*

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator

/**
 *
 * @author kristofer.eriksson@openfellas.com
 */
class ClientSessionAspectTests extends GrailsUnitTestCase {

    def appCtx

    protected void setUp() {
        super.setUp()

        mockLogging(OpenERPClientService, true)

        mockConfig """
            openerp.baseurl = 'http://smarterp.zoirosit.de:8069/xmlrpc'
            openerp.db = 'eaitest'
            openerp.usr = 'admin'
            openerp.pwd = 'stadion'
        """

		def bb = new BeanBuilder()

        bb.beans {
            openerpClientSessionAutoProxyCreator(AnnotationAwareAspectJAutoProxyCreator) {
                proxyTargetClass = true
            }

            openerpClientSessionAspect(ClientSessionAspect) {
            }

            clientSessionTest(ClientSessionTestService)
        }

        appCtx = bb.createApplicationContext()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testReadPartner() {
        def partnerId = 47

        def service = appCtx.getBean("clientSessionTest")

        def res = service.readPartner(partnerId)
        println(res)
    }
}

class ClientSessionTestService {

    @ClientSession
    def readPartner(partner_id) {
        def res = ClientSessionHolder.exec('res.partner', 'read', [partner_id], [])

		return res
    }
}
