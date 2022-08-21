/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

@file:OptIn(ExperimentalFoundationApi::class)

package com.yourcompany.android.lazycomposabletutorial.ui.cats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.lazycomposabletutorial.data.model.Cat
import com.yourcompany.android.lazycomposabletutorial.ui.theme.LazyComposableTheme
import kotlinx.coroutines.launch
import com.yourcompany.android.lazycomposabletutorial.R
import com.yourcompany.android.lazycomposabletutorial.ui.theme.primaryDarkColor

@Composable
fun CuteCatsApp(viewModel: CatsFeedViewModel) {

  val isLoading by viewModel.isLoading.collectAsState()
  val cats by viewModel.cats.collectAsState()
  val showGrid by viewModel.showGrid.collectAsState()

  LazyComposableTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
      Scaffold(
          topBar = { CatsAppBar(viewModel, showGrid) },
      ) {
        if (isLoading) LoadingScreen()
        else CatsFeed(cats, showGrid)
      }
    }
  }
}

@Composable
fun CatsAppBar(viewModel: CatsFeedViewModel, showGrid: Boolean) {

  // TODO: Use remember for showGrid

  TopAppBar(
      title = { Text(text = "Cute cats") },
      actions = {
        IconButton(
            onClick = { viewModel.showGrid.value = !viewModel.showGrid.value },
        ) {
          Icon(
              painterResource(
                  id = if (showGrid) {
                    R.drawable.ic_baseline_list_24
                  } else {
                    R.drawable.ic_baseline_grid_on_24
                  },
              ),
              "Icon to change from grid to list and back",
          )
        }
      },
  )
}

@Composable
fun CatsFeed(cats: List<Cat>, showGrid: Boolean) {
  val lazyListState = rememberLazyListState()

  // TODO: Use derivedStateOf to check index state

  if (showGrid) LazyGridCats(cats = cats, state = lazyListState)
  else LazyListCats(cats = cats, state = lazyListState)

  Column(
      modifier = Modifier
          .fillMaxHeight()
          .fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Bottom,
  ) {
    // TODO: Show button when index is bigger than the first
  }
}

@Composable
fun LazyGridCats(cats: List<Cat>, state: LazyListState) {
  // TODO: Display cats in grid view
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
    ) {
        item {
            CuteCatsHeader()
        }
        items(cats) {
            CatItem(cat = it)
        }
    }
}

@Composable
fun LazyListCats(cats: List<Cat>, state: LazyListState) {
  // TODO: Show cats in a scrollable column
    LazyColumn {
        stickyHeader {
            CuteCatsHeader()
        }
        items(cats) {
            CatItem(cat = it)
        }
    }
}

@Composable
fun CuteCatsHeader() {
  Box(
      modifier = Modifier
          .fillMaxWidth()
          .background(Color.White),
      contentAlignment = Alignment.Center,
  ) {
    Text(text = "The cutest cats in the world!", fontSize = 24.sp, color = primaryDarkColor)
  }
}

@Composable
fun ScrollToTop(state: LazyListState) {
  val coroutineScope = rememberCoroutineScope()
  Button(
      onClick = {
        // TODO: Animate list state to the first index
      },
      modifier = Modifier.padding(8.dp)
  ) {
    Text(text = "Scroll to top", modifier = Modifier.padding(8.dp))
  }
}

@Composable
fun LoadingScreen() {
  Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CircularProgressIndicator()
  }
}

