<template>
  <section class="history-version-section">
    <pre v-html="formattedJson"></pre>
  </section>
</template>

<script setup lang="ts">
const props = defineProps({
  selectedVersion: {
    type: Object as () => GenericAudit,
    required: true,
  },
  versionToCompareTo: {
    type: Object as () => GenericAudit,
    required: true,
  },
  highlightColor: {
    type: String,
    required: true,
  },
});

const formattedJson = computed(() => {
  const selectedVersionJson = JSON.stringify(props.selectedVersion, null, 2);
  const versionToCompareToJson = JSON.stringify(
    props.versionToCompareTo,
    null,
    2
  );

  let result = selectedVersionJson ?? "";
  /*let j = 0;
  let selectedChar = selectedVersionJson.charAt(0);
  let comparedChar = versionToCompareToJson.charAt(0);
  for (let i = 0; i < versionToCompareToJson.length; i++) {
    selectedChar = selectedVersionJson.charAt(j);
    comparedChar = versionToCompareToJson.charAt(i);
    if (selectedChar !== comparedChar) {
      // ajout d'un span qui contient tous les caractères qui ne sont pas dans versionToCompareToJson jusqu'au prochain coup ou selectedChar === comparedChar
      let newSpan = `<span style="color: ${props.highlightColor}">`;
      while (selectedChar !== comparedChar && j < selectedVersionJson.length) {
        newSpan += selectedChar;
        j++;
        selectedChar = selectedVersionJson.charAt(j);
      }
      newSpan += "</span>";
      result += newSpan;
      result += selectedChar;
      j++;
    } else {
      result += selectedChar;
      j++;
    }

    if (j === selectedVersionJson.length) {
      break;
    }
  }*/

  return result;
});
</script>

<style scoped>
.history-version-section {
  margin: var(--min-margin);
  flex-grow: 1;
}
</style>
