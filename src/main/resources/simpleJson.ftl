[
<#list results as result>
  {
    "id": "${result.id}"
    "name": "${result.name}",
    "success": ${result.success?c},
    "time": "${result.testInstant}"
  }
</#list>
]