import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Courses from "@/views/Courses";
import CourseDetails from "@/views/CourseDetails";
import InstructorPage from "@/views/InstructorPage";
import Profile from "@/views/Profile";
import Logout from "@/components/auth/Logout";
import VerifyEmail from "@/views/VerifyEmail";
import UploadCourse from "@/views/UploadCourse";
import UploadLessons from "@/views/UploadLessons";
import InstructorCourses from "@/views/InstructorCourses";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/logout',
    name: 'Logout',
    component: Logout
  },
  {
    path: '/verify-email',
    name: 'VerifyEmail',
    component: VerifyEmail
  },
  {
    path: '/courses/:slug',
    name: 'Courses',
    component: Courses,
  },
  {
    path: '/course-details/:id',
    name: 'CourseDetails',
    component: CourseDetails,
    props: true
  },
  {
    path: '/instructor',
    name: 'InstructorPage',
    component: InstructorPage,
    meta: { completed: true }
  },
  {
    path: '/instructor/profile',
    name: 'Profile',
    component: Profile,
  },
  {
    path: '/instructor/courses/upload-course',
    name: 'UploadCourse',
    component: UploadCourse,
  },
  {
    path: '/instructor/courses',
    name: 'InstructorCourses',
    component: InstructorCourses,
  },
  {
    path: '/instructor/courses/upload-course/:id',
    name: 'UploadLessons',
    component: UploadLessons,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, next) {
    return { x: 0, y: 0}
  }
})

// router.beforeEach((to, from, next) => {
//   if (to.matched.some(record => record.meta.completed)) {
//     console.log(store.getters.GET_USER)
//     if (mapGetters.GET_USER.is_teacher && mapGetters.GET_USER.is_completed) {
//       next({
//         name: 'Profile'
//       })
//     }
//     else {
//       next()
//     }
//   }
//   else {
//     next()
//   }
// })

export default router
