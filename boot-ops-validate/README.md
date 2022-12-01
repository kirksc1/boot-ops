# Boot-Ops-Validate
The BootOps Validate project provides validation capabilities to the BootOps process
including a 'validate' command that can be used to validate GitOps pull requests
prior to merging.

## Runtime Dependencies
BootOps Validate does leverage the Jakarta Validation API.  In order to utilize
its functionality the following APIs (or later versions) are required in the runtime.
- jakarta.el:jakarta.el-api:4.0.0
- org.glassfish:jakarta.el:4.0.2