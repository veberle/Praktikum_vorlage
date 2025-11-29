<#include "../_head.ftl">
<#include "../_nav.ftl">

<main class="max-w-2xl mx-auto p-6">
  <h1 class="text-2xl font-semibold text-gray-800 mb-6">${(skill.id)?has_content?string("Bearbeiten","Neu")} Skill</h1>

  <form method="post" action="/skills" class="space-y-4 bg-white p-6 rounded-lg shadow border border-gray-200">
    <input type="hidden" name="id" value="${skill.id!}"/>

    <div>
      <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
      <input id="name" type="text" name="name" value="${skill.name!""}"
             class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"/>
    </div>
    <div>
      <label for="description" class="block text-sm font-medium text-gray-700">Beschreibung</label>
      <textarea id="description" name="description" rows="4"
                class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">${skill.description!""}</textarea>
    </div>
    <div>
      <label for="category" class="block text-sm font-medium text-gray-700">Kategorie</label>
      <select id="category" name="skillCategory.id"
              class="mt-1 block w-full rounded-md border border-gray-300 bg-white shadow-sm focus:border-blue-500 focus:ring-blue-500">
        <option value="">-- bitte wählen --</option>
        <#list categories as c>
          <option value="${c.id}" ${(skill.skillCategory?? && skill.skillCategory.id == c.id)?then('selected','')}>
            ${c.name}
          </option>
        </#list>
      </select>
    </div>

    <div class="flex justify-end gap-2">
      <a href="/skills" class="px-4 py-2 text-sm rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50">Abbrechen</a>
      <button type="submit" class="px-4 py-2 text-sm rounded-md bg-blue-600 text-white hover:bg-blue-700">Speichern</button>
    </div>
  </form>
</main>

<#include "../_footer.ftl">
