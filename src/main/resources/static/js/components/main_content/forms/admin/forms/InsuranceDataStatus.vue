<template>
  <RadioButtonGroup ref="rbg"
                    :small="true"
                    title="Страховые данные"
                    :editable="hasInsuranceData"
                    :items="insuranceDataStatus"
                    :selectIndex="insuranceDataStatus.find(item => item.status === insuranceStatus).index"
                    :changeListener="true"/>
</template>

<script>
import RadioButtonGroup from "../../../../items/RadioButtonGroup.vue";

export default {
  components: {RadioButtonGroup},
  props: ['insuranceStatus', 'hasInsuranceData'],
  data() {
    return {
      insuranceDataStatus: [
        {
          name: '❌',
          status: 'NONE',
          title: 'Нет данных',
          index: 0
        },
        {
          name: '✔️',
          status: 'CONFIRMED',
          title: 'Подтвердить',
          index: 1
        },
        {
          name: '⌛',
          status: 'WAIT_CONFIRMATION',
          title: 'Ожидает проверки',
          index: 2
        },
        {
          name: '🚫',
          status: 'DENIED',
          title: 'Отклонить',
          index: 3
        }
      ]
    }
  },
  methods: {
    getNewValue() {
      return this.$refs.rbg.getNewValue()
    },
    updateStartValue() {
      return this.$refs.rbg.updateStartValue()
    }
  }
}
</script>

<style scoped>

</style>