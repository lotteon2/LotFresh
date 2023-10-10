package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the `libs` extension.
*/
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final JavaLibraryAccessors laccForJavaLibraryAccessors = new JavaLibraryAccessors(owner);
    private final SpringLibraryAccessors laccForSpringLibraryAccessors = new SpringLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers) {
        super(config, providers);
    }

        /**
         * Creates a dependency provider for jpa (org.springframework.boot:spring-boot-starter-data-jpa)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJpa() { return create("jpa"); }

        /**
         * Creates a dependency provider for lombok (org.projectlombok:lombok)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLombok() { return create("lombok"); }

        /**
         * Creates a dependency provider for sl4j (org.slf4j:slf4j-api)
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSl4j() { return create("sl4j"); }

    /**
     * Returns the group of libraries at java
     */
    public JavaLibraryAccessors getJava() { return laccForJavaLibraryAccessors; }

    /**
     * Returns the group of libraries at spring
     */
    public SpringLibraryAccessors getSpring() { return laccForSpringLibraryAccessors; }

    /**
     * Returns the group of versions at versions
     */
    public VersionAccessors getVersions() { return vaccForVersionAccessors; }

    /**
     * Returns the group of bundles at bundles
     */
    public BundleAccessors getBundles() { return baccForBundleAccessors; }

    public static class JavaLibraryAccessors extends SubDependencyFactory {

        public JavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for validation (javax.validation:validation-api)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getValidation() { return create("java-validation"); }

    }

    public static class SpringLibraryAccessors extends SubDependencyFactory {

        public SpringLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

            /**
             * Creates a dependency provider for web (org.springframework:spring-web)
             * This dependency was declared in catalog libs.versions.toml
             */
            public Provider<MinimalExternalModuleDependency> getWeb() { return create("spring-web"); }

    }

    public static class VersionAccessors extends VersionFactory {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}
