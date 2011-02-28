package org.grails.plugins.openerp.client

import org.apache.xmlrpc.XmlRpcException
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl

import org.apache.commons.logging.LogFactory

/**
 *
 * @author kristofer.eriksson@openfellas.com
 */
class ClientSessionHolder {

    private final static log = LogFactory.getLog(ClientSessionHolder)

	private static final ThreadLocal<XmlRpcClient> clientHolder = new ThreadLocal<XmlRpcClient>();
    private static final ThreadLocal<Map> configHolder = new ThreadLocal<Map>();

    public static void init(baseUrl, db, usr, pwd) {
        def loginClient = createClient(baseUrl + '/common')
        def uid = login(loginClient, db, usr, pwd)

        def client = createClient(baseUrl + '/object')

        clientHolder.set(client)
        configHolder.set([uid: uid, db: db, usr: usr, pwd: pwd])
    }

    public static Object exec(model, operation, Object... args) {
        def data = [configHolder.get().db, configHolder.get().uid, configHolder.get().pwd, model, operation]

        args.each{
            data << it
        }

        log.debug('executing: ' + data)

        return clientHolder.get().execute('execute', data)
    }

    public static void close() {
        clientHolder.remove()
        configHolder.remove()
    }

    public static getClient() {
        return clientHolder.get()
    }

    public static getConfig() {
        return configHolder.get()
    }

    private static createClient(url) {
		def config = new XmlRpcClientConfigImpl()
		try {
			config.setServerURL(new URL(url))
	    } catch (MalformedURLException e) {
	    	e.printStackTrace();
	    }

	    def client = new XmlRpcClient()
	    client.setConfig(config)

        return client
    }

    private static login(client, db, usr, pwd) {
	    def params = [db, usr, pwd] as Object[]
		try {
			return client.execute("login", params)
		} catch (XmlRpcException e) {
			e.printStackTrace();

            return -1
		}
    }
}

