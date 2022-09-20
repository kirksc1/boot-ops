# Boot-Ops
[![circleci](https://circleci.com/gh/kirksc1/boot-ops.svg?style=svg)](https://circleci.com/gh/kirksc1/boot-ops)
[![codecov](https://codecov.io/gh/kirksc1/boot-ops/branch/main/graph/badge.svg?token=d2IyjyL7FW)](https://codecov.io/gh/kirksc1/boot-ops)
## Overview
Boot-Ops is a framework designed to aid in the creation of [GitOps][gitops] 
infrastructure automation processes using the [Spring Boot][springboot] platform.
It offers an event-based approach to processing state changes that can be parallelized
and easily expanded or adapted to individual needs.

## Core Commands
| Name | Description |
|---|---|
| [converge][converge] | Apply the Item and associated data to infrastructure |

[gitops]: https://about.gitlab.com/topics/gitops/
[springboot]: https://spring.io/projects/spring-boot/
[converge]: docs/CONVERGE.md