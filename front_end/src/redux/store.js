import { configureStore, createSlice } from "@reduxjs/toolkit";

let Id = createSlice({
  name: "Id",
  initialState: null,
  reducers: {
    changeId(state, action) {
      return action.payload;
    },
  },
});

export default configureStore({
  reducer: {
    userId: Id.reducer,
  },
});

export let { changeId } = Id.actions;
