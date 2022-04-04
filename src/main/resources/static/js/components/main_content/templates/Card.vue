<template>
  <div class="card">
    <div class="card-title">
      <div>
        <slot name="title"></slot>
      </div>
    </div>
    <div class="card-body">
      <div>
        <slot name="body"></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Card"
}
</script>

<style scoped>
.card {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 100%;
  height: 100%;
  margin: 15px;
  box-shadow: -5px 5px 15px rgba(114, 199, 211, 0.8);
  overflow: visible;
}

.card-title {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #462446;
  text-align: center;
  min-height: 50px;
  width: 100%;
  font-size: 24px;
  font-weight: bold;
  color: white;
  padding: 2px;
}

.card-body {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  background-color: #FFC153;
  height: 100%;
  width: 100%;
  padding: 5px;
  font-size: 22px;
}
</style>