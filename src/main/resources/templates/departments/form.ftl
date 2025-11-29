<#include "../_head.ftl">
<#include "../_nav.ftl">

<main class="max-w-2xl mx-auto p-6">
  <h1 class="text-2xl font-semibold text-gray-800 mb-6">${(department.id)?has_content?string("Bearbeiten","Neu")} Abteilung</h1>

  <form method="post" action="/departments" class="space-y-4 bg-white p-6 rounded-lg shadow border border-gray-200">
    <input type="hidden" name="id" value="${department.id!}"/>
    <div>
      <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
      <input id="name" type="text" name="name" value="${department.name!""}"
             class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"/>
    </div>
    <div>
      <label for="description" class="block text-sm font-medium text-gray-700">Beschreibung</label>
      <textarea id="description" name="description"
                class="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" rows="4">${department.description!""}</textarea>
    </div>
    <div class="flex justify-end gap-2">
      <a href="/departments" class="px-4 py-2 text-sm rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50">Abbrechen</a>
      <button type="submit" class="px-4 py-2 text-sm rounded-md bg-blue-600 text-white hover:bg-blue-700">Speichern</button>
    </div>
  </form>
</main>

<#include "../_footer.ftl">
