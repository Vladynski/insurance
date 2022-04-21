<template>
  <div class="block-column" style="justify-content: flex-start">
    <div class="block-row block-row-right" style="height: auto; padding: 10px">
      <Button class="btn green-btn d-btn-max-size" :click="createFaq">Добавить новый вопрос</Button>
    </div>
    <div class="block-column block-items-margin def-block-vertical-scrolling"
         style="justify-content: flex-start;">
      <template v-for="(item, index) in faqList" :key="item.question">
        <FaqCard :id="item.id" :question="item.question" :answer="item.answer" :deleteFunction="deleteFaq"
                 :editable="true" :startEditMode="item.id===undefined"/>
        <hr v-if="index !== faqList.length - 1" class="separator">
      </template>
    </div>
  </div>
</template>

<script>
import FaqCard from "../cards/FaqCard.vue";
import Button from "../../../items/Button.vue";

export default {
  components: {Button, FaqCard},

  data() {
    return {
      faqList: undefined,
      canAdd: true
    }
  },
  methods: {
    deleteFaq(id) {
      if (id === undefined) {
        this.faqList.shift()
        return;
      }

      let deleteIndex = undefined
      this.faqList.forEach((el, index) => {
        if (el.id === id)
          deleteIndex = index
      })
      if (deleteIndex || deleteIndex === 0)
        this.faqList.splice(deleteIndex, 1)
    },
    createFaq() {
      this.faqList.unshift({
        id: undefined,
        answer: '',
        question: ''
      })
    }
  },
  mounted() {
    this.$api.getFaqList().then(
        (ok) => {
          this.faqList = ok.data
        }
    )
  }
}
</script>

<style scoped>

</style>