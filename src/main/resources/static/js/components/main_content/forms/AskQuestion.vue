<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container ask-title-background" style="font-size: 3.5vmin">
      Здесь можно задать вопрос
    </div>
    <div class="base-form-data def-block" style="padding: 5%">

    </div>
  </div>
</template>

<script>
export default {
  name: "AskQuestion"
}
</script>

<style scoped>
.ask-title-background:after {
  background-image: url("../../pic/question.png");
  background-color: #008CBA;
}
</style>