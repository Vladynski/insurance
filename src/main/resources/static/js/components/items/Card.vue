<template>
  <div class="card">
    <div class="card-title">
      {{ title }}
    </div>
    <div class="card-body">
      {{ contentBody }}
    </div>
    <div class="card-edit-body block-row" v-if="editable">
      <Input ref="input" style="margin-right: 5px" :maxlength="maxlength" :keydown="keydown" :value="contentBody"
             :placeholder="title" labelColor="white"/>
      <Button style="height: 32px; width: 35px" class="btn blue-btn btn-img" title="edit"
              :click="clickEdit"><img src="edit.png" alt="edit" style="width: 20px; height: 20px">
      </Button>
    </div>
  </div>
</template>

<script>
import Button from "./Button.vue";
import Input from "./Input.vue";

export default {
  props: ['body', 'title', 'editable', 'editClick', 'keydown', 'maxlength'],
  data() {
    return {
      contentBody: this.body
    }
  },
  components: {Button, Input},
  methods: {
    getInputText() {
      return this.$refs.input.getText()
    },
    setContentBody(text) {
      this.contentBody = text
    },
    clickEdit() {
      this.$refs.input.update()
      this.editClick(this.getInputText()).then(
          (ok) => {
            let placeholderText = this.$refs.input.getPlaceholder();
            this.$refs.input.setPlaceholder('Изменено')
            this.contentBody = this.getInputText()
            setTimeout(() => {
              this.$refs.input.setPlaceholder(placeholderText)
            }, 3000)
          },
          (err) => {
            const responseData = err.response.data;
            if (err.response.status === 400) {
              const errorData = responseData.data
              this.$refs.input.setBadText(errorData[Object.keys(errorData)[0]])
            } else if (responseData.message) {
              this.$refs.input.setBadText(responseData.message)
            } else {
              this.$refs.input.setBadText('Неизвестная ошибка')
            }
          }
      )
    }
  }
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
  position: relative;
  background-color: #FFC153;
  box-shadow: -5px 5px 15px rgba(114, 199, 211, 0.8);
  overflow: hidden;
  transition-duration: 300ms;
}

.card:hover {
  transform: scale(1.05, 1.05);
}

.card:hover .card-edit-body {
  bottom: 0;
}

.card:not(:hover) .card-edit-body {
  bottom: -53px;
  transition-delay: 1500ms;
  transition-property: bottom;
}

.card-title {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #462446;
  text-align: center;
  /*min-height: 50px;*/
  height: 30%;
  width: 100%;
  /*font-size: 24px;*/
  font-size: 2vmin;
  font-weight: bold;
  color: white;
  padding: 2px;
}

.card-body {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  overflow-wrap: anywhere;
  background-color: transparent;
  height: 100%;
  width: 100%;
  padding: 15px;
  font-size: 22px;
}

.card-edit-body {
  background-color: rgb(70, 36, 70, 0.5);
  height: 53px;
  width: 100%;
  padding: 2px 5px;
  position: absolute;
  bottom: -53px;
  justify-content: flex-end;
  align-items: flex-end;
}

/*.card-edit-body {*/
/*  background-color: rgb(70, 36, 70, 0.95);*/
/*  height: 100%;*/
/*  width: 100%;*/
/*  padding: 2px 5px;*/
/*  position: absolute;*/
/*  bottom: -100%;*/
/*  justify-content: flex-end;*/
/*  align-items: center;*/
/*}*/
</style>