<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container ask-title-background">
      Здесь можно задать вопрос
    </div>
    <div class="base-form-data def-block" style="padding: 5%">
      <PageSlider ref="pageSlider" :hideTitle="true">
        <Slide ref="myQuestionsSlide"
               :pageSliderGetter="getPageSlider">
          <InfoFrame ref="infoFrame"/>
          <div class="block-column block-column-items-margin" style="justify-content: flex-start">
            <InfoFrame v-if="myQuestions.length === 0" defText="У вас нет заданных вопросов" :constant="true"
                       :outsideType="info"/>
            <div v-else class="block-column def-block-vertical-scrolling" style="justify-content: flex-start">
              <UserQuestionForm v-for="question in myQuestions" :question="question"/>
            </div>
            <Button class="btn green-btn d-btn-max-size" :click="switchToAskQuestionForm">Задать вопрос</Button>
          </div>
        </Slide>
        <Slide ref="askQuestionSlide"
               :pageSliderGetter="getPageSlider">
          <div class="block-column" style="overflow: hidden">
            <div class="block-column ask-space-between" style="justify-content: center; padding: 5px">
          <TextArea ref="questionText"
                    title="Введи интересующий вас вопрос"
                    placeholder="Что делать если хочется кушац?.. :(">
            Перед тем как задать вопрос, <b>убедитесь что на него уже нет ответа в разделе FAQ</b>. Ответ на свой вопрос вы получите по электронной почте, на которую зарегистрирован аккаунт, а также сможете посмотреть его в разделе <i>Задать вопрос</i>.
          </TextArea>
              <InfoFrame ref="infoFrame"/>
              <div class="block-row block-row-items-margin" style="height: auto">
                <Button class="btn green-btn d-btn-max-size" :click="askQuestion" style="margin-top: 15px">
                  Отправить
                </Button>
                <Button class="btn blue-btn d-btn-max-size" :click="switchToMyQuestionsForm" style="margin-top: 15px">
                  К списку вопросов
                </Button>
              </div>
            </div>
          </div>
        </Slide>
      </PageSlider>
    </div>
  </div>
</template>

<script>
import TextArea from "../../items/TextArea.vue";
import Button from "../../items/Button.vue";
import InfoFrame from "../../items/InfoFrame.vue";
import PageSlider from "../../items/slider/PageSlider.vue";
import Slide from "../../items/slider/Slide.vue";
import UserQuestionForm from "./admin/forms/UserQuestionForm.vue";

export default {
  components: {UserQuestionForm, Slide, PageSlider, TextArea, Button, InfoFrame},
  data() {
    return {
      myQuestions: []
    }
  },
  methods: {
    switchToAskQuestionForm() {
      this.$refs.pageSlider.nextSlide()
    },
    switchToMyQuestionsForm() {
      this.$refs.pageSlider.previewSlide()
    },
    getPageSlider() {
      return this.$refs.pageSlider
    },
    askQuestion() {
      if (this.$refs.questionText.test()) {
        this.$api.sendQuestion(this.$refs.questionText.getText()).then(
            (ok) => {
              this.myQuestions.push({
                id: ok.data,
                text: this.$refs.questionText.getText(),
                text_answer: 'Ожидает ответа...'
              })
              this.$refs.questionText.clear()
              this.$refs.infoFrame.showOk("Ваш вопрос отправлен")
            },
            (err) => {
              this.$api.errorHandler(err, () => this.$refs.infoFrame)
            }
        );
      }
    }
  },
  beforeMount() {
    this.$api.getMyQuestions().then((ok) => {
      let data = ok.data

      data.forEach(question => {
        if (!question.text_answer)
          question.text_answer = 'Ожидает ответа...'
      })

      this.myQuestions = data
    }, (err) => {
      this.$api.errorHandler(err, () => this.$refs.infoFrame)
    })
  },
  mounted() {
    this.$refs.pageSlider.initSlides([
      this.$refs.myQuestionsSlide,
      this.$refs.askQuestionSlide
    ])
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