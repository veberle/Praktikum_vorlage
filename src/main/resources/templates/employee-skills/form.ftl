<#include "../_head.ftl">
<#include "../_nav.ftl">

<main class="max-w-2xl mx-auto p-6">
  <h1 class="text-2xl font-semibold text-gray-800 mb-6">${(employeeSkill.id)?has_content?string("Bearbeiten","Neu")} Mitarbeiter-Skill</h1>

  <form method="post" action="/employee-skills" class="space-y-4 bg-white p-6 rounded-lg shadow border border-gray-200">
    <input type="hidden" name="id" value="${employeeSkill.id!}"/>

    <div>
      <label for="employee" class="block text-sm font-medium text-gray-700">Mitarbeiter</label>
      <select id="employee" name="employee.id"
              class="mt-1 block w-full rounded-md border-gray-300 bg-white shadow-sm focus:border-blue-500 focus:ring-blue-500">
        <option value="">-- bitte wählen --</option>
        <#list employees as e>
          <option value="${e.id}" ${(employeeSkill.employee?? && employeeSkill.employee.id == e.id)?then('selected','')}>
            ${e.firstName} ${e.lastName}
          </option>
        </#list>
      </select>
    </div>

    <div>
      <label for="skill" class="block text-sm font-medium text-gray-700">Skill</label>
      <select id="skill" name="skill.id"
              class="mt-1 block w-full rounded-md border-gray-300 bg-white shadow-sm focus:border-blue-500 focus:ring-blue-500">
        <option value="">-- bitte wählen --</option>
        <#list skills as s>
          <option value="${s.id}" ${(employeeSkill.skill?? && employeeSkill.skill.id == s.id)?then('selected','')}>
            ${s.name}
          </option>
        </#list>
      </select>
    </div>

    <div>
      <label for="level" class="block text-sm font-medium text-gray-700">Level (1–100)</label>
      <input id="level" type="number" name="level" min="1" max="100" value="${employeeSkill.level!1}"
             class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"/>
    </div>

    <div class="flex justify-end gap-2">
      <a href="/employee-skills" class="px-4 py-2 text-sm rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50">Abbrechen</a>
      <button type="submit" class="px-4 py-2 text-sm rounded-md bg-blue-600 text-white hover:bg-blue-700">Speichern</button>
    </div>
  </form>
</main>

<#include "../_footer.ftl">
