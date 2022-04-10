<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container ask-title-background">
      Здесь можно задать вопрос
    </div>
    <div class="base-form-data def-block" style="padding: 5%">
      <div class="block-column" style="overflow: hidden;">
        <div class="block-column ask-space-between" style="justify-content: center; padding: 5px">
          <TextArea ref="questionText"
                    title="Введи интересующий вас вопрос"
                    placeholder="Что делать если хочется кушац?.. :(">
            Перед тем как задать вопрос, <b>убедитесь что на него уже нет ответа в разделе FAQ</b>. Ответ на свой вопрос вы получите по электронной почте, на которую зарегистрирован аккаунт.
          </TextArea>
          <InfoFrame ref="infoFrame"/>
          <div class="block-row block-row-right" style="height: auto">
            <Button class="btn green-btn d-btn-max-size" :click="askQuestion" style="margin-top: 15px">Отправить
            </Button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TextArea from "../../items/TextArea.vue";
import Button from "../../items/Button.vue";
import InfoFrame from "../../items/InfoFrame.vue";

export default {
  components: {TextArea, Button, InfoFrame},
  methods: {
    askQuestion() {
      if (this.$refs.questionText.test()) {
        this.$api.sendQuestion(this.$refs.questionText.getText()).then(
            (ok) => {
              this.$refs.questionText.clear()
              this.$refs.infoFrame.showOk("Ваш вопрос отправлен")
            },
            (err) => {
              this.$api.errorHandler(err, () => this.$refs.infoFrame)
            }
        );
      }
    }
  }
}
</script>

<style scoped>
.ask-title-background:after {
  background-image: url("../../pic/question.png");
  background-color: rgba(56, 110, 57, 0.78);
}
.ask-space-between > div {
  margin-bottom: 15px;
}
</style>