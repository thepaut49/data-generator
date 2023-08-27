import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import NetworkError from "@/views/errors/NetworkError.vue";
import NotFound from "@/views/errors/NotFound.vue";
import SampleDataLayout from "../views/SampleData/SampleDataLayout.vue";
import SampleDataEdit from "../views/SampleData/SampleDataEdit.vue";
import SampleDataDetails from "../views/SampleData/SampleDataDetails.vue";
import SampleDataHistory from "../views/SampleData/SampleDataHistory.vue";
import SampleDatas from "../views/SampleData/SampleDatas.vue";
import SampleDataCategoryLayout from "../views/SampleDataCategory/SampleDataCategoryLayout.vue";
import SampleDataCategoryEdit from "../views/SampleDataCategory/SampleDataCategoryEdit.vue";
import SampleDataCategoryDetails from "../views/SampleDataCategory/SampleDataCategoryDetails.vue";
import SampleDataCategoryHistory from "../views/SampleDataCategory/SampleDataCategoryHistory.vue";
import SampleDataCategories from "../views/SampleDataCategory/SampleDataCategories.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/Sample-data-categories/list",
      name: "SampleDataCategories",
      component: SampleDataCategories,
    },
    {
      path: "/Sample-data-categories/:categoryName?",
      name: "SampleDataCategoryLayout",
      props: true,
      component: SampleDataCategoryLayout,
      children: [
        {
          path: "details",
          name: "SampleDataCategoryDetails",
          component: SampleDataCategoryDetails,
        },
        {
          path: "history",
          name: "SampleDataCategoryHistory",
          component: SampleDataCategoryHistory,
        },
        {
          path: "edit",
          name: "SampleDataCategoryEdit",
          component: SampleDataCategoryEdit,
        },
      ],
    },
    {
      path: "/Sample-data-categories/create",
      name: "SampleDataCategoryEdit",
      component: SampleDataCategoryEdit,
    },
    {
      path: "/Sample-datas/list",
      name: "SampleDatas",
      component: SampleDatas,
    },
    {
      path: "/Sample-datas/:keyProp?",
      name: "SampleDataLayout",
      props: true,
      component: SampleDataLayout,
      children: [
        {
          path: "details",
          name: "SampleDataDetails",
          component: SampleDataDetails,
        },
        {
          path: "history",
          name: "SampleDataHistory",
          component: SampleDataHistory,
        },
        {
          path: "edit",
          name: "SampleDataEdit",
          component: SampleDataEdit,
        },
      ],
    },
    {
      path: "/Sample-datas/create",
      name: "SampleDataEdit",
      component: SampleDataEdit,
    },
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/AboutView.vue"),
    },
    {
      path: "/:catchAll(.*)",
      name: "NotFound",
      component: NotFound,
    },
    {
      path: "/404/:resource",
      name: "NotFound",
      component: NotFound,
      props: true,
    },
    {
      path: "/network-error",
      name: "NetworkError",
      component: NetworkError,
    },
  ],
});

export default router;
