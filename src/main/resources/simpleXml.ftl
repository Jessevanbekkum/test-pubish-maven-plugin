<results>
    <#list results as result>
        <result>
            <id>${result.id}</id>
            <name>${result.name}</name>
            <success>${result.success?c}</success>
            <time>${result.testInstant}</time>
        </result>
    </#list>
</results>

