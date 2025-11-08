# dynamic-enum
A tiny project to explore dynamic enum style class and its comparison to static java enum

## How to build and upload to central.sonatype.org
1. You can just use ```mvn clean test verify```  for local testing
2. Run javadoc, and sonar:sonar and any other validations to ensure everything is in order
3. Do ```git add```, ```git commit``` and ```git push origin main``` .
4. ```scripts/release.sh```  will directly upload the artifact

[![Build](https://img.shields.io/github/actions/workflow/status/venkateshamurthy/dynamic-enum/publish-main.yml?branch=main&logo=github&style=for-the-badge)](https://github.com/venkateshamurthy/dynamic-enum/actions)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.venkateshamurthy/dynamic-enum.svg?color=blue&logo=apachemaven&style=for-the-badge&cacheSeconds=30)](https://central.sonatype.com/artifact/io.github.venkateshamurthy/dynamic-enum)
[![Javadocs](https://javadoc.io/badge2/io.github.venkateshamurthy/dynamic-enum/javadoc.svg?style=for-the-badge&&cacheSeconds=30)](https://javadoc.io/doc/io.github.venkateshamurthy/dynamic-enum)
[![License](https://img.shields.io/github/license/venkateshamurthy/dynamic-enum.svg?style=for-the-badge&cacheSeconds=60)](https://github.com/venkateshamurthy/dynamic-enum/blob/main/LICENSE)
[![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=openjdk&style=for-the-badge)](https://openjdk.org/)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=io.github.venkateshamurthy:dynamic-enum&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=io.github.venkateshamurthy%3Adynamic-enum&branch=main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.venkateshamurthy%3Adynamic-enum&metric=alert_status&branch=main&cacheSeconds=30)](https://sonarcloud.io/summary/overall?id=io.github.venkateshamurthy%3Adynamic-enum&branch=main)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=io.github.venkateshamurthy%3Adynamic-enum&metric=coverage)](https://sonarcloud.io/summary/new_code?id=io.github.venkateshamurthy%3Adynamic-enum)
![OWASP Dependency-Check](https://img.shields.io/badge/OWASP%20Scan-Passed-brightgreen)
---

