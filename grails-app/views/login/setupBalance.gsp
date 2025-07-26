<h2>Set Your Starting Balance</h2>
<p>Welcome, ${user.name}! Please enter your starting balance to continue.</p>

<g:if test="${flash.message}">
    <div style="color: red;">${flash.message}</div>
</g:if>

<g:form controller="login" action="saveBalance">
    <label>Starting Balance (ZAR):</label>
    <g:field type="number" name="startingBalance" step="0.01" required="true"/>
    <br/>
    <g:submitButton name="save" value="Save and Continue"/>
</g:form>
