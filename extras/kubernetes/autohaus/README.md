# autohaus

![Version: 2023.10.0](https://img.shields.io/badge/Version-2023.10.0-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 2023.10.0-buildpacks](https://img.shields.io/badge/AppVersion-2023.10.0--buildpacks-informational?style=flat-square)

Helm Chart für autohaus

**Homepage:** <https://www.www.h-ka.de>

## Maintainers

| Name | Email | Url |
| ---- | ------ | --- |
| Jürgen Zimmermann | <Juergen.Zimmermann@h-ka.de> | <https://www.h-ka.de> |

## Requirements

Kubernetes: `>=1.28.0`

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| autoscaling.cpuUtilizationPercentage | int | `80` | Maximale CPU-Auslastung |
| autoscaling.maxReplicas | int | `100` | Maximale Anzahl an Replicas |
| autoscaling.memoryUtilizationPercentage | int | `80` | Maximale RAM-Auslastung |
| autoscaling.minReplicas | int | `1` | Mininmale Anzahl an Replicas |
| datasource.password | string | `"Change Me!"` |  |
| datasource.url | string | `"jdbc:postgresql://localhost/autohaus"` |  |
| datasource.username | string | `"autohaus"` |  |
| deployment.containerPort | int | `8080` | Port innerhalb des Containers |
| fullnameOverride | string | `""` | _Fully Qualified Name (FQN)_ ist defaultmäßig im Chart bei `name` und kann überschrieben werden. |
| gid | int | `1000` | ID der Linux-Gruppe |
| h2.console.enabled | string | `"false"` |  |
| http2 | string | `"true"` | Flag, ob HTTP2 genutzt wird |
| image.name | string | `"autohaus"` | Name des Image |
| image.pullPolicy | string | `"Always"` | Pull-Policy für das Image |
| image.repository | string | `"juergenzimmermann"` | Repository als Präfix beim Image-Namen |
| image.tag | string | `""` | Defaultwert ist im Chart bei `appVersion` und kann überschrieben werden. |
| livenessProbe.failureThreshold | int | `3` | Max. Anzahl an Fehlversuchen bei den Liveness-Proben |
| livenessProbe.initialDelay | int | `0` | Anzahl Sekunden, bis die Probe für Liveness abgesetzt wird |
| livenessProbe.period | int | `10` | periodischer Abstand zwischen den Liveness-Proben in Sekunden |
| livenessProbe.timeout | int | `1` | Timeout für Liveness-Probe in Sekunden |
| logfile | string | `"/tmp/application.log"` | Logdatei |
| nameOverride | string | `""` | Defaultwert ist im Chart bei `name` und kann überschrieben werden. |
| namespace | string | `"acme"` | Namespace in NOTES.txt |
| profile | string | `"default"` | Profile für Spring als Umgebungsvariable |
| readinessProbe.failureThreshold | int | `3` | Max. Anzahl an Fehlversuchen bei den Readiness-Proben |
| readinessProbe.initialDelay | int | `0` | Anzahl Sekunden, bis die Probe für Readiness abgesetzt wird |
| readinessProbe.period | int | `10` | periodischer Abstand zwischen den Readiness-Proben in Sekunden |
| readinessProbe.timeout | int | `1` | Timeout für Readiness-Probe in Sekunden |
| replicaCount | int | `2` | Anzahl Replica im Pod von Kubernetes |
| resourcesLimits.cpu | string | `"600m"` | Maximalanforderung an CPU-Ressourcen in _millicores_, z.B. `500m` oder `1` |
| resourcesLimits.ephemeral | string | `"64Mi"` | Maximalanforderung an flüchtigen Speicher für z.B. Caching und Logs |
| resourcesLimits.memory | string | `"512Mi"` | Maximalanforderung an Memory-Resourcen als _mebibyte_ Wert |
| resourcesRequests.cpu | Mindest- | `"500m"` | Anforderung an CPU-Ressourcen in _millicores_, z.B. `500m` oder `1` |
| resourcesRequests.ephemeral | Mindest- | `"64Mi"` | Anforderung an flüchtigen Speicher für z.B. Caching und Logs |
| resourcesRequests.memory | Mindest- | `"512Mi"` | Anforderung an Memory-Resourcen als _mebibyte_ Wert |
| servicePort | int | `8080` | Port des Kubernetes-Service |
| ssl | string | `"true"` | Flag, ob TLS genutzt wird |
| uid | int | `1002` | ID des Linux-Users |
| versionSuffix | string | `nil` | Version als Suffix in deployment.yaml für Canary-Releases |

----------------------------------------------
Autogenerated from chart metadata using [helm-docs v1.11.2](https://github.com/norwoodj/helm-docs/releases/v1.11.2)