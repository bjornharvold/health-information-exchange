package com.hxcel.globalhealth.platform.utils;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.springframework.osgi.util.OsgiBundleUtils;
import org.springframework.osgi.util.OsgiStringUtils;

import java.util.Dictionary;
import java.util.Date;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: May 8, 2009
 * Time: 3:40:23 PM
 * Responsibility: Convenience class for injecting Bundle interface with more information
 */
public class BundleWrapper implements Comparable {
    private final Bundle bundle;
    private final BundleHeaderParser parser = new BundleHeaderParser();

    public BundleWrapper(Bundle bundle) {
        this.bundle = bundle;
    }

    public String getSymbolicName() {
        return OsgiStringUtils.nullSafeSymbolicName(bundle);
    }

    public String getLocation() {
        return bundle.getLocation();
    }

    public Date getLastModified() {
        return new Date(bundle.getLastModified());
    }

    public ServiceReference[] getRegisteredServices() {
        return bundle.getRegisteredServices();
    }

    public ServiceReference[] getServicesInUse() {
        return bundle.getServicesInUse();
    }

    public long getBundleId() {
        return bundle.getBundleId();
    }

    public String getState() {
        String result = null;

        switch (bundle.getState()) {
            case Bundle.ACTIVE:
                result = "ACTIVE";
                break;
            case Bundle.INSTALLED:
                result = "INSTALLED";
                break;
            case Bundle.RESOLVED:
                result = "RESOLVED";
                break;
            case Bundle.STARTING:
                result = "STARTING";
                break;
            case Bundle.STOPPING:
                result = "STOPPING";
                break;
            case Bundle.UNINSTALLED:
                result = "UNINSTALLED";
        }

        return result;
    }

    public Dictionary getHeaders() {
        return bundle.getHeaders();
    }

    public Boolean getIsSystem() {
        return OsgiBundleUtils.isSystemBundle(bundle);
    }

    public Boolean getIsFragment() {
        return OsgiBundleUtils.isFragment(bundle);
    }

    public Boolean getIsActive() {
        return OsgiBundleUtils.isBundleActive(bundle);
    }

    public Boolean getIsResolved() {
        return OsgiBundleUtils.isBundleResolved(bundle);
    }

    public Version getVersion() {
        return OsgiBundleUtils.getBundleVersion(bundle);
    }

    public String getManifestVersion() {
        return (String) bundle.getHeaders().get("Manifest-Version");
    }

    public String getBundleActivator() {
        return (String) bundle.getHeaders().get("Bundle-Activator");
    }

    public String getWebContextPath() {
        return (String) bundle.getHeaders().get("Web-ContextPath");
    }

    public String getBundleRequiredExecutionEnvironment() {
        return (String) bundle.getHeaders().get("Bundle-RequiredExecutionEnvironment");
    }

    public List<ExportImportPackage> getExportPackages() {
        return parser.parseExportImportPackage((String) bundle.getHeaders().get("Export-Package"));
    }

    public String getBundleVersion() {
        return (String) bundle.getHeaders().get("Bundle-Version");
    }

    public List<ExportImportPackage> getImportPackages() {
        return parser.parseExportImportPackage((String) bundle.getHeaders().get("Import-Package"));
    }

    public String getEclipseSystemBundle() {
        return (String) bundle.getHeaders().get("Eclipse-SystemBundle");
    }

    public String getBundleCopyright() {
        return (String) bundle.getHeaders().get("Bundle-Copyright");
    }

    public String getBundleName() {
        return (String) bundle.getHeaders().get("Bundle-Name");
    }

    public String getBundleVendor() {
        return (String) bundle.getHeaders().get("Bundle-Vendor");
    }

    public String getBundleDescription() {
        return (String) bundle.getHeaders().get("Bundle-Description");
    }

    public String getBundleDocUrl() {
        return (String) bundle.getHeaders().get("Bundle-DocUrl");
    }

    public String getBundleManifestVersion() {
        return (String) bundle.getHeaders().get("Bundle-ManfestVersion");
    }

    public String getExportService() {
        return (String) bundle.getHeaders().get("Export-Service");
    }

    public String getMainClass() {
        return (String) bundle.getHeaders().get("Main-Class");
    }

    public String getBundleLocalization() {
        return (String) bundle.getHeaders().get("Bundle-Localization");
    }

    public String getEclipseExtensibleApi() {
        return (String) bundle.getHeaders().get("Eclipse-ExtensibleAPI");
    }

    public String getCreatedBy() {
        return (String) bundle.getHeaders().get("Created-By");
    }

    public String getAntVersion() {
        return (String) bundle.getHeaders().get("Ant-Version");
    }

    public String getImplementationVendor() {
        return (String) bundle.getHeaders().get("Implementation-Vendor");
    }

    public String getImplementationTitle() {
        return (String) bundle.getHeaders().get("Implementation-Title");
    }

    public String getImplementationVersion() {
        return (String) bundle.getHeaders().get("Implementation-Version");
    }

    public String getBuiltBy() {
        return (String) bundle.getHeaders().get("Built-By");
    }

    public String getArchiverVersion() {
        return (String) bundle.getHeaders().get("Archiver-Version");
    }

    public String getBuildJdk() {
        return (String) bundle.getHeaders().get("Build-Jdk");
    }

    public String getFragmentHost() {
        return (String) bundle.getHeaders().get("Fragment-Host");
    }


    @Override
    public int compareTo(Object o) {
        BundleWrapper b = (BundleWrapper) o;

        String bundleX = bundle.getSymbolicName();
        String bundleY = b.getSymbolicName();

        return bundleX.compareTo(bundleY);
    }
}
