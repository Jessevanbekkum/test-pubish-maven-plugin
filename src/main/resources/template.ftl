[
  <#list results as result>
  {
    "id": "${result.name}",
    "success": ${result.success?c},
    "time": "${result.testInstant}"
  }
  </#list>
]