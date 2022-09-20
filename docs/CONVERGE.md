# Converge Command
The 'converge' command is the core process by which the item manifest
is applied to the appropriate infrastructure.  Boot Ops executes this 
process through publishing of events to signal the start of each stage.
Converge behavior can be added at each of these stages by implementing
concrete applications of the 
[ApplicationListener<ApplicationEvent>][application-listener] interface.

## Events
| Event | Description | Typical Usage | Status |
|---|---|---|---|
| SystemInitiatedEvent | Occurs on initial start of the process | Validate the infrastructure is ready to be updated | Pending |
| ItemJsonReadEvent | Json/Yaml-specific, Occurs once the Json/Yaml has been read | JsonNode manipulation prior to deserialization | Pending |
| ItemInitiatedEvent | Deserialization of manifest into an Item is complete | Population of defaults or other opinions | Pending |
| ItemInitializedEvent | Item and associated data is fully populated | Validation of Item data for structure and policy | Pending |
| ItemValidatedEvent | Item and associated data has been validated | Validation error processing, tracking of failure metrics and diagnostics | Pending |
| ItemConvergeInitiatedEvent | Item and associated data is ready to be converged | Apply Item and associated data to infrastructure | Pending |
| ItemConvergeCompletedEvent | Item and associated data has been converged | Tracking of converge or change metrics | Pending |
| ItemCompletedEvent | Item processing has completed | Tracking of metrics related to Items | Pending |
| SystemCompletedEvent | Occurs at the completion of all Item processing | Tracking of metrics related to the entire process | Pending |

[application-listener]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationListener.html