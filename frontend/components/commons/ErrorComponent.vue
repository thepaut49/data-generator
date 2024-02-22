<template>
  <section class="message is-danger" v-show="message">
    <div class="message-header">
      <p>{{ props.title }}</p>
    </div>
    <div class="message-body">
      {{ props.message }}
    </div>
  </section>
</template>

<script lang="ts" setup>
const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  message: {
    required: true,
  },
});
</script>

<style scoped>
.message.is-danger {
  background-color: #feecf0;
}

.message.is-danger .message-header {
  background-color: #f14668;
  color: #fff;
}

.message-header {
  align-items: center;
  background-color: #494949;
  border-radius: 0.375em 0.375em 0 0;
  color: #fff;
  display: flex;
  font-weight: 700;
  justify-content: space-between;
  line-height: 1.25;
  padding: 0.75em 1em;
  position: relative;
}

.message {
  background-color: #f4f4f4;
  border-radius: 0.375em;
  font-size: 1rem;
}
</style>
