<#include "../_head.ftl">
<#include "../_nav.ftl">

<main class="max-w-7xl mx-auto p-6">
  <div class="flex items-center justify-between mb-6">
    <h1 class="text-2xl font-semibold text-gray-800">Skills</h1>
    <a href="/skills/new"
       class="inline-flex items-center rounded-md bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700">Neuer Skill</a>
  </div>

  <#if msgSuccess??>
    <div class="mb-4 rounded-md bg-green-50 p-4 text-green-800 border border-green-200">${msgSuccess}</div>
  </#if>
  <#if msgError??>
    <div class="mb-4 rounded-md bg-red-50 p-4 text-red-800 border border-red-200">${msgError}</div>
  </#if>

  <#-- Gruppierte Darstellung: pro Kategorie eine eigene Tabelle -->
  <#list categories![] as c>
    <div class="mb-8">
      <h2 class="text-xl font-semibold text-gray-800 mb-3">${c.name}</h2>
      <div class="overflow-hidden rounded-lg border border-gray-200 bg-white shadow-sm">
        <table class="min-w-full divide-y divide-gray-200">
          <thead class="bg-gray-50">
          <tr>
            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-gray-600">ID</th>
            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-gray-600">Name</th>
            <th class="px-4 py-3 text-left text-xs font-semibold uppercase tracking-wider text-gray-600">Beschreibung</th>
            <th class="px-4 py-3 text-right text-xs font-semibold uppercase tracking-wider text-gray-600">Aktionen</th>
          </tr>
          </thead>
          <tbody class="divide-y divide-gray-100 bg-white">
          <#assign anyRow=false>
          <#list skills![] as s>
            <#if s.skillCategory?? && s.skillCategory.id == c.id>
              <#assign anyRow=true>
              <tr class="hover:bg-gray-50">
                <td class="px-4 py-3 text-sm text-gray-700">${s.id}</td>
                <td class="px-4 py-3 text-sm text-gray-900">${s.name}</td>
                <td class="px-4 py-3 text-sm text-gray-700">${s.description!""}</td>
                <td class="px-4 py-3 text-right">
                  <a href="/skills/${s.id}/edit" class="inline-flex items-center rounded-md bg-white px-3 py-1.5 text-xs font-medium text-gray-700 border border-gray-300 hover:bg-gray-50">Bearbeiten</a>
                  <form method="post" action="/skills/${s.id}/delete" class="inline ml-2"
                        onsubmit="return confirm('Skill wirklich löschen?');">
                    <button type="submit" class="inline-flex items-center rounded-md bg-red-600 px-3 py-1.5 text-xs font-medium text-white hover:bg-red-700">Löschen</button>
                  </form>
                </td>
              </tr>
            </#if>
          </#list>

          <#if !anyRow>
            <tr>
              <td colspan="4" class="px-4 py-3 text-sm text-gray-500">Keine Skills in dieser Kategorie.</td>
            </tr>
          </#if>
          </tbody>
        </table>
      </div>
    </div>
  </#list>
</main>

<#include "../_footer.ftl">
