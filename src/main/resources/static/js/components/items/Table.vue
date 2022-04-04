<template>
  <table :class="['def-table', small ? 'small-table' : '']">
    <tr>
      <th v-for="item in title">{{ item }}</th>
    </tr>
    <tr v-for="el in data">
      <td v-for="item in el">{{ item }}</td>
    </tr>
  </table>
</template>

<script>
export default {
  props: ['title', 'data', 'small']
}
</script>

<style scoped>
.def-table {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

.def-table td, .def-table th {
  border: 1px solid #ddd;
  padding: 8px;
}

.def-table > 2tr:nth-child(even) {
  background-color: #f2f2f2;
}

.def-table > tr:hover {
  background-color: #ddd;
}

.def-table th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}

.small-table {
  font-size: 16px;
}

.small-table th{
  padding-top: 8px;
  padding-bottom: 8px;
}

.small-table td, .small-table th {
  border: 1px solid #ddd;
  padding: 6px;
}
</style>