<#include "../_head.ftl">
<#include "../_nav.ftl">

<main class="max-w-7xl mx-auto p-6">
  <div class="flex items-center justify-between mb-6">
    <h1 class="text-2xl font-semibold text-gray-800">Mitarbeiter</h1>
    <a href="/employees/new"
       class="inline-flex items-center rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700">Neuer Mitarbeiter</a>
  </div>

  <#if msgSuccess??>
    <div class="mb-4 rounded-md bg-green-50 p-4 text-green-800 border border-green-200">${msgSuccess}</div>
  </#if>
  <#if msgError??>
    <div class="mb-4 rounded-md bg-red-50 p-4 text-red-800 border border-red-200">${msgError}</div>
  </#if>

  <#-- Gruppierte Darstellung: pro Abteilung eine eigene Tabelle -->
  <#list departments![] as d>
    <div class="mb-8">
      <h2 class="text-xl font-semibold text-gray-800 mb-3">${d.name}</h2>
      <div class="overflow-hidden rounded-lg border border-gray-200 bg-white shadow-sm">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
          <tr>
            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-gray-600">ID</th>
            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-gray-600">Name</th>
            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-gray-600">Email</th>
            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-gray-600">Aktiv</th>
            <th class="px-4 py-3 text-right text-xs font-semibold uppercase tracking-wider text-gray-600">Aktionen</th>
          </tr>
          </thead>
          <tbody class="divide-y divide-gray-100 bg-white">
          <#assign anyRow=false>
          <#list employees![] as e>
            <#if e.department?? && e.department.id == d.id>
              <#assign anyRow=true>
              <tr class="hover:bg-gray-50">
                <td class="px-4 py-3 text-sm text-gray-700">${e.id}</td>
                <td class="px-4 py-3 text-sm text-gray-900">${e.firstName} ${e.lastName}</td>
                <td class="px-4 py-3 text-sm text-gray-700">${e.email}</td>
                <td class="px-4 py-3 text-sm text-gray-700">${e.active?string("Ja","Nein")}</td>
                <td class="px-4 py-3 text-right">
                  <a href="/employees/${e.id}/edit" class="inline-flex items-center rounded-md bg-white px-3 py-1.5 text-xs font-medium text-gray-700 border border-gray-300 hover:bg-gray-50">Bearbeiten</a>
                  <form method="post" action="/employees/${e.id}/delete" class="inline ml-2"
                        onsubmit="return confirm('Mitarbeiter wirklich löschen?');">
                    <button type="submit" class="inline-flex items-center rounded-md bg-red-600 px-3 py-1.5 text-xs font-medium text-white hover:bg-red-700">Löschen</button>
                  </form>
                </td>
              </tr>
            </#if>
          </#list>

          <#if !anyRow>
            <tr>
              <td colspan="5" class="px-4 py-3 text-sm text-gray-500">Keine Mitarbeiter in dieser Abteilung.</td>
            </tr>
          </#if>
          </tbody>
        </table>
      </div>
    </div>
  </#list>
</main>

<#include "../_footer.ftl">
