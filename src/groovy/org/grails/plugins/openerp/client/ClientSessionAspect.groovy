package org.grails.plugins.openerp.client

import org.springframework.beans.factory.InitializingBean
import org.codehaus.groovy.grails.commons.ConfigurationHolder

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

import org.apache.commons.logging.LogFactory

import org.grails.plugins.openerp.client.ClientSessionHolder as CLH

/**
 *
 * @author kristofer.eriksson@openfellas.com
 */
@Aspect
class ClientSessionAspect implements InitializingBean {

	def log = LogFactory.getLog(ClientSessionAspect)

    def baseurl
    def db
    def usr
    def pwd

    void afterPropertiesSet() {
        //config
        def config = ConfigurationHolder.config

        if(!baseurl && config.openerp.baseurl) {
           baseurl = config.openerp.baseurl
        }
        if(!db && config.openerp.db) {
           db = config.openerp.db
        }
        if(!usr && config.openerp.usr) {
           usr = config.openerp.usr
        }
        if(!pwd && config.openerp.baseurl) {
           pwd = config.openerp.pwd
        }
    }

    @Around("@annotation(clientsession)")
    public Object invoke(ProceedingJoinPoint method, ClientSession clientsession) throws Throwable {
        log.debug "intercepting '${method.signature.name}'..."

        boolean clientCreated = false

        def result = null

        try {
            if(!CLH.getClient()) {
                CLH.init(baseurl, db, usr, pwd)

                log.debug "[method: ${method.signature.name}] no client in thread, create..."
                clientCreated = true
            }

            result = method.proceed()

        } finally {
            if(clientCreated) {
                log.debug "[method: ${method.signature.name}] ...shutting down client, closing"
                CLH.close()
            }
        }

        log.debug "...leaving"
        return result
    }
}

