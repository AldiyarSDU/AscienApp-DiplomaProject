<template>
  <footer>
    <div class="container footer">
      <div class="row">
        <div class="col-xl-3">
          <router-link to="/">
            <img class="logo" src="../../assets/white_logo.svg" alt="logo">
          </router-link>
        </div>
        <div class="col-xl-9">
          <div class="row">
            <div class="col-xl-4">
              <p class="footer_title">Для учеников школ</p>
              <div class="links">
                <router-link to="">Математика</router-link>
                <router-link to="">Английский язык</router-link>
                <router-link to="">Подготовка к ЕНТ</router-link>
                <router-link to="">Пробные Тесты 2021</router-link>
              </div>
            </div>
            <div class="col-xl-4">
              <p class="footer_title">Для студентов</p>
              <div class="links">
                <router-link to="">Языки Программирования</router-link>
                <router-link to="">Маркетинг</router-link>
                <router-link to="">Проф языки</router-link>
              </div>
            </div>
            <div class="col-xl-4">
              <p class="footer_title">О нас</p>
              <div class="links">
                <router-link to="">Контакты</router-link>
                <router-link to="">Учителя</router-link>
                <router-link to="">О нас</router-link>
                <router-link to="">Помощь</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="copy_right">
      <p>© 2021 Ascien.ca. Все права защищены. </p>
    </div>
  </footer>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style lang="scss" scoped>
  @import "../../assets/variables";
  footer {
    padding: 10px 0;
    background: $primary;
    .logo {
      width: 70%;
    }
    .footer {
      padding: 50px 15px;
      .copy_right {
        //margin-top: 50px;
      }
    }
    .links a {
      display: block;
      text-decoration: none;
      font-size: 14px;
      line-height: 30px;
      color: #FFFFFF;
    }
    .copy_right {
      p {
        color: #FFFFFF;
        text-align: center;
      }
    }
  }
</style>