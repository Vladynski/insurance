<template>
  <div class="faq block-column">
    <div class="faq-title block-row">
      {{ question }}
    </div>
    <div class="faq-body">
      {{ answer }}
    </div>
  </div>
</template>

<script>
export default {
  props: ['question', 'answer']
}
</script>

<style scoped>
.faq {
  font-family: "Segoe UI",serif;
  width: 100%;
  height: auto;
  background-color: white;
  padding: 5px;
  border: 1px solid white;
  border-radius: 5px;
  box-shadow: rgba(168, 155, 155, 0.61) -1px 2px 5px;
}

.faq:hover {
  transform: scale(1.02, 1.02);
}

.faq-title {
  background-color: #57E534;
  padding: 10px 30px;
  margin-bottom: 10px;
  border-radius: 5px 5px 0 0;
  font-weight: bold;
}

.faq-body {
  padding: 15px;
  text-indent: 15px;
  border-radius: 0 0 5px 5px;
  background: linear-gradient(45grad, #e5ffe5, #e5faff);
  text-align: justify;
}
</style>