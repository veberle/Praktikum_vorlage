<#include "../_head.ftl">
<#include "../_nav.ftl">

<main class="max-w-2xl mx-auto p-6">
  <h1 class="text-2xl font-semibold text-gray-800 mb-6">${(employee.id)?has_content?string("Bearbeiten","Neu")} Mitarbeiter</h1>

  <form method="post" action="/employees" class="space-y-4 bg-white p-6 rounded-lg shadow border border-gray-200">
    <input type="hidden" name="id" value="${employee.id!}"/>

    <div>
      <label for="firstName" class="block text-sm font-medium text-gray-700">Vorname</label>
      <input id="firstName" type="text" name="firstName" value="${employee.firstName!""}"
             class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"/>
    </div>
    <div>
      <label for="lastName" class="block text-sm font-medium text-gray-700">Nachname</label>
      <input id="lastName" type="text" name="lastName" value="${employee.lastName!""}"
             class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"/>
    </div>
    <div>
      <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
      <input id="email" type="email" name="email" value="${employee.email!""}"
             class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"/>
    </div>
    <div>
      <label for="department" class="block text-sm font-medium text-gray-700">Abteilung</label>
      <select id="department" name="department.id"
              class="mt-1 block w-full rounded-md border border-gray-300 bg-white shadow-sm focus:border-blue-500 focus:ring-blue-500">
        <option value="">-- bitte wählen --</option>
        <#list departments as d>
          <option value="${d.id}" ${(employee.department?? && employee.department.id == d.id)?then('selected','')}>
            ${d.name}
          </option>
        </#list>
      </select>
    </div>
    <div class="flex items-center gap-2">
      <input id="active" type="checkbox" name="active" value="true" ${(employee.active!true)?string('checked','')}
             class="h-4 w-4 rounded border border-gray-300 text-blue-600 focus:ring-blue-500">
      <label for="active" class="text-sm text-gray-700">Aktiv</label>
    </div>

    <div class="flex justify-end gap-2">
      <a href="/employees" class="px-4 py-2 text-sm rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50">Abbrechen</a>
      <button type="submit" class="px-4 py-2 text-sm rounded-md bg-blue-600 text-white hover:bg-blue-700">Speichern</button>
    </div>
  </form>
</main>

<#include "../_footer.ftl">
