<template>
  <div class="block-column user-question block-column-items-margin">
    <div class="block-row user-question-text">
      {{ question.text }}
    </div>
    <div class="block-column user-question-body">
      <template v-if="question.text_answer">
        <div class="user-question-text-answer">
          {{ question.text_answer }}
        </div>
      </template>
      <template v-else>
        <div v-if="answerMode" class="block-column block-column-items-margin user-question-answer-area">
          <InfoFrame ref="infoFrame"/>
          <TextArea ref="textArea" title="Ответ" :maxlength="1024" placeholder="Текст ответа"/>
          <div class="block-row block-row-items-margin" style="height: auto">
            <Button class="btn green-btn d-btn-max-size" :click="sendAnswer">Отправить</Button>
            <Button class="btn blue-btn d-btn-max-size" :click="changeMode">Отмена</Button>
          </div>
        </div>
        <div v-else class="block-row center">
          <Button class="btn green-btn d-btn-max-size" :click="changeMode">Ответить</Button>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import TextArea from "../../../../items/TextArea.vue";
import Button from "../../../../items/Button.vue";
import InfoFrame from "../../../../items/InfoFrame.vue";

export default {
  components: {InfoFrame, Button, TextArea},
  props: ['question', 'sentOk'],
  data() {
    return {
      answerMode: false
    }
  },
  methods: {
    sendAnswer() {
      if (this.$refs.textArea.test()) {
        this.$adminApi.setQuestionAnswer(this.question.id, this.$refs.textArea.getText()).then(
            (ok) => {
              this.question.text_answer = this.$refs.textArea.getText()
              if (this.sentOk)
                this.sentOk()
            }, (err) => {
              this.$api.errorHandler(err, () => this.$refs.infoFrame)
            }
        )
      }
    },
    changeMode() {
      this.answerMode = !this.answerMode
    }
  }
}
</script>

<style scoped>
.user-question {
  background: linear-gradient(45grad, #7ed99e, #b0d9d8);
  box-shadow: gray -1px 2px 5px;
  justify-content: flex-start;
  height: auto;
  padding: 20px;
  border-radius: 5px;
  cursor: default;
}

.user-question:hover {
  transform: scale(1.01, 1.01);
}

.user-question-text {
  font-weight: bold;
}

.user-question-text-answer {
  padding: 10px;
  margin: 25px 0 0 25px;
  font-size: 28px;
  border-radius: 5px;
  background-color: rgba(255, 255, 255, 0.2);
  overflow-wrap: anywhere;
  width: 100%;
}

.user-question-body {
}

.user-question-answer-area {
  justify-content: flex-start;
  height: 300px;
}
</style>