<template>
  <div class="bouncer-container">
    <div class="bouncer">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    return {};
  },
};
</script>

<style scoped>
.bouncer {
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  width: 100px;
  height: 100px;
}
.bouncer-container {
  display: flex;
  align-items: center;
  justify-content: space-around;
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  background-color: rgb(0, 0, 0); /* Fallback color */
  background-color: rgba(0, 0, 0, 0.4);
}

.bouncer div {
  width: 20px;
  height: 20px;
  background: #0077ff;
  border-radius: 50%;
  animation: bouncer 0.5s cubic-bezier(0.19, 0.57, 0.3, 0.98) infinite alternate;
}

.bouncer div:nth-child(2) {
  animation-delay: 0.1s;
  opacity: 0.8;
}
.bouncer div:nth-child(3) {
  animation-delay: 0.2s;
  opacity: 0.6;
}
.bouncer div:nth-child(4) {
  animation-delay: 0.3s;
  opacity: 0.4;
}

@keyframes bouncer {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(-100px);
  }
}
</style>
