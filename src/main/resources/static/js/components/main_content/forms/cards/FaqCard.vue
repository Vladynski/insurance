<template>
  <div class="faq block-column">
    <div class="faq-title block-row">
      <Input v-if="edit" ref="question" placeholder="" inputClass="d-input-size" :value="aQuestion"/>
      <template v-else>
        {{ aQuestion }}
      </template>
    </div>
    <div class="faq-body block-row">
      <div v-if="edit" class="block-column block-column-items-margin">
        <TextArea ref="answer" :maxlength="1024" title="Ответ на вопрос" :value="aAnswer"
                  placeholder="Ответь  в меня"
                  style="min-height: 250px"/>
        <div class="block-row block-row-items-margin">
          <Button ref="updateButton" class="btn d-btn-max-size green-btn" :click="update">{{ aId ? 'Обновить' : 'Добавить'  }}</Button>
          <Button ref="deleteButton" class="btn d-btn-max-size red-btn" :click="deleteFaq">Удалить</Button>
          <Button ref="cancelButton" class="btn d-btn-max-size blue-btn" :click="switchEditMode">Отменить</Button>
        </div>
      </div>
      <template v-else>
        {{ aAnswer }}
      </template>
    </div>
    <button v-if="editable && !edit" class="btn fq-edit-btn green-btn" @click="switchEditMode">
      ✏
    </button>
  </div>
</template>

<script>
import TextArea from "../../../items/TextArea.vue";
import Input from "../../../items/Input.vue";
import Button from "../../../items/Button.vue";

export default {
  components: {Button, Input, TextArea},
  props: ['id', 'question', 'answer', 'deleteFunction', 'editable', 'startEditMode'],
  data() {
    //a - actual
    return {
      edit: this.startEditMode,
      aQuestion: this.question,
      aAnswer: this.answer,
      aId: this.id
    }
  },
  methods: {
    deleteFaq() {
      if (this.aId > 0) {
        this.$adminApi.deleteFaq(this.aId).then((ok) => {
          this.deleteFunction(this.id)
        }, (err) => {
          this.$api.errorHandler(err, () => this.$refs.question)
        })
      } else {
        this.deleteFunction(this.id)
      }
    },
    update() {
      if (this.$refs.question.test() && this.$refs.answer.test()) {
        this.aQuestion = this.$refs.question.getText()
        this.aAnswer = this.$refs.answer.getText()
        this.blockButtons()
        if (this.id) {
          this.processResponse(this.$adminApi.updateFaq(this.id, this.aQuestion, this.aAnswer))
        } else {
          this.processResponse(this.$adminApi.addFaq(this.aQuestion, this.aAnswer))
        }
        this.switchEditMode()
      }
    },
    processResponse(response) {
      response.then((ok) => {
        this.aId = ok.data
      }, (err) => {
        this.$api.errorHandler(err, () => this.$refs.question)
        this.unblockButtons()
      })
    },
    blockButtons() {
      this.$refs.updateButton.block()
      this.$refs.deleteButton.block()
      this.$refs.cancelButton.block()
    },
    unblockButtons() {
      this.$refs.updateButton.unblock()
      this.$refs.deleteButton.unblock()
      this.$refs.cancelButton.unblock()
    },
    switchEditMode() {
      this.edit = !this.edit
    }
  }
}
</script>

<style scoped>
.faq {
  font-family: "Segoe UI", serif;
  width: 100%;
  height: auto;
  background-color: white;
  padding: 5px;
  border: 1px solid white;
  border-radius: 5px;
  box-shadow: rgba(168, 155, 155, 0.61) -1px 2px 5px;
  position: relative;
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

.fq-edit-btn {
  position: absolute;
  bottom: 0;
  right: 0;
}
</style>