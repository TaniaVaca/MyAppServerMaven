package com.mycompany.myappserver.utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.mycompany.myappserver.utils.logger.Log;


/**
 * The Class ConfigProperties.
 * 
 * @author Hader Ceron
 * @version 1.04 September 24 / 2012
 */
public class ConfigProperties {

	/** The Constant CONFIG_PROPERTIES_FILE_NAME. */
	public static  String CONFIG_PROPERTIES_FILE_NAME = System.getenv("CONFIGURATION_MAIN_PROPERTIES");

	/** The config properties. */
	public static Properties configProperties;
	
	
	/** PROPERTIES *. */
	public static String ldapInitialContextFactory;

	/** The ldap protocol. */
	public static String ldapProtocol;

	/** The ldap base dn. */
	public static String ldapBaseDN;

	/** The ldap ip. */
	public static String ldapIP;

	/** The ldap port. */
	public static String ldapPort;

	/** The ldap security authentication. */
	public static String ldapSecurityAuthentication ;

	/** The ldap security principal. */
	public static String ldapSecurityPrincipal;

	/** The ldap security credential. */
	public static String ldapSecurityCredential ;

	/** The ldap sn attr. */
	public static String ldapSNAttr;

	/** The ldap uid attr. */
	public static String ldapUIDAttr;

	/** The ldap display name attr. */
	public static String ldapDisplayNameAttr;

	/** The ldap user password attr. */
	public static String ldapUserPasswordAttr;

	/** The ldap cn attr. */
	public static String ldapCNAttr;

	/** The ldap entry dn attr. */
	public static String ldapEntryDNAttr;

	/** The ldap is member of attr. */
	public static String ldapIsMemberOfAttr;

	/** The ldap mail attr. */
	public static String ldapMailAttr;

	/** The ldap policy attr. */
	public static String ldapPolicyAttr;

	/** The ldap unique member attr. */
	public static String ldapUniqueMemberAttr;

	/** The ldap car license attr. */
	public static String ldapCarLicenseAttr;
	
	/** The ldap organization attr. */
	public static String ldapOrganizationAttr;
	
	/** The ldap unit organizational attr. */
	public static String ldapUnitOrganizationalAttr;


	static{
		try {
			configProperties = new Properties();
			configProperties.load(new FileInputStream(CONFIG_PROPERTIES_FILE_NAME)); 
			
			ldapInitialContextFactory = configProperties.getProperty("ldap.initialContextFactory");
			ldapProtocol = configProperties.getProperty("ldap.protocol");
			ldapBaseDN = configProperties.getProperty("ldap.baseDN");
			ldapIP = configProperties.getProperty("ldap.ip");
			ldapPort = configProperties.getProperty("ldap.port");
			ldapSecurityAuthentication =configProperties.getProperty("ldap.securityAuthentication");
			ldapSecurityPrincipal=	configProperties.getProperty("ldap.securityPrincipal");	
			ldapSecurityCredential =configProperties.getProperty("ldap.securityCredential");
			ldapSNAttr=configProperties.getProperty("ldap.SNAttr");		
			ldapUIDAttr=configProperties.getProperty("ldap.UIDAttr");		
			ldapDisplayNameAttr=configProperties.getProperty("ldap.displayNameAttr");
			ldapUserPasswordAttr=configProperties.getProperty("ldap.userPasswordAttr");
			ldapCNAttr=configProperties.getProperty("ldap.CNAttr");
			ldapEntryDNAttr=configProperties.getProperty("ldap.entryDNAttr");
			ldapIsMemberOfAttr=configProperties.getProperty("ldap.isMemberOfAttr");
			ldapMailAttr=configProperties.getProperty("ldap.mailAttr");
			ldapPolicyAttr=configProperties.getProperty("ldap.policyAttr");
			ldapUniqueMemberAttr=configProperties.getProperty("ldap.uniqueMemberAttr");
			ldapCarLicenseAttr=configProperties.getProperty("ldap.carLicenseAttr");
			ldapOrganizationAttr=configProperties.getProperty("ldap.organizationAttr");
			ldapUnitOrganizationalAttr = configProperties.getProperty("ldap.unitOrganizationalAttr");
			
		} catch (IOException ioe) {
			Log.error ("ConfigProperties.static init - IOException : "+ioe.getMessage());
		}catch (Exception e) {
			Log.error ("ConfigProperties.static init - Exception : "+e.getMessage());
		}
	}
	
	/**
	 * Gets the config property.
	 *
	 * @param key the key
	 * @return the config property
	 */
	public static String getConfigProperty(String key){
		return configProperties.getProperty(key);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(ConfigProperties.ldapBaseDN);
	}
}
