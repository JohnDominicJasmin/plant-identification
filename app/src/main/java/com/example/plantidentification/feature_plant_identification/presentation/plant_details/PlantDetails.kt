package com.example.plantidentification.feature_plant_identification.presentation.plant_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plantidentification.R
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.MainState
import com.example.plantidentification.ui.theme.PlantIdentificationTheme
import java.util.*


@Composable
fun PlantInformation(state: MainState, navController: NavController, onClickBackButton: () -> Unit = {}) {

    PlantDetailsContent(state = state, onClickBackButton = {
        onClickBackButton()
        navController.popBackStack()
    })

}

@Composable
fun PlantDetailsContent(state: MainState = MainState(), onClickBackButton: () -> Unit = {}) {


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (state.isPlant == false) {
            PlantNotFoundPlaceholder(onClickBackButton)
        } else {

            Image(
                painter = painterResource(id = R.drawable.batangas_lakelands_review_14_1__8_),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight)

            Surface(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 0.dp)
                    .fillMaxSize(),
                color = MaterialTheme.colors.background, shape = RoundedCornerShape(12.dp)) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(22.dp)) {

                    item {

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                                .padding(bottom = 2.dp, top = 6.dp),
                            text = state.plantName.uppercase(Locale.ROOT),
                            fontSize = TextUnit(24f, TextUnitType.Sp),
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Visible,
                            color = Color.Black,
                            textAlign = TextAlign.Center)


                        Text(
                            text = "Probability: ${state.plantProbability}",
                            fontSize = TextUnit(16f, TextUnitType.Sp),
                            color = Color.Black,
                            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp),
                            textAlign = TextAlign.Center)


                        AsyncImage(
                            modifier = Modifier
                                .aspectRatio(1.5f)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .padding(all = 8.dp),
                            model = state.plantImageUrl,
                            contentDescription = "Plant Image",
                            contentScale = ContentScale.Fit,
                            placeholder = painterResource(id = R.drawable.placeholder))

                        Text(
                            modifier = Modifier.padding(all = 15.dp),
                            text = state.plantDescription,
                            style = TextStyle(
                                textAlign = TextAlign.Start,
                                fontSize = TextUnit(16f, TextUnitType.Sp),
                                lineHeight = 23.sp,
                            ), overflow = TextOverflow.Clip)


                        Button(
                            onClick = onClickBackButton,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3D5420)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Back",
                                color = Color.White,
                                fontSize = TextUnit(16f, TextUnitType.Sp),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
                        }

                        Spacer(modifier = Modifier.padding(bottom = 12.dp))


                    }

                }
            }
        }
    }
}

@Composable
fun PlantNotFoundPlaceholder(onClickOkayButton: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.ic_notfound),
            contentDescription = "Plant not found Icon",
            modifier = Modifier
                .padding(all = 7.dp)
                .size(160.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Plant not found",
            fontSize = TextUnit(24f, TextUnitType.Sp),
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray
        )

        Button(
            onClick = onClickOkayButton,
            modifier = Modifier.padding(top = 70.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3D5420)),
            shape = RoundedCornerShape(12.dp)) {
            Text(
                text = "Okay",
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp))
        }
    }
}

@Preview
@Composable
fun PreviewPlaceholder() {
    PlantIdentificationTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
            PlantNotFoundPlaceholder()
        }
    }
}

@Preview
@Composable
fun PreviewPlantDetails() {
    PlantDetailsContent(
        state = MainState(
            plantName = "blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium",
            plantImageUrl = "https://cdn.eyeem.com/thumb/4c6754110370bb1b6faf95d6e1d888ca9c3a9cc8-1570026251491/640/480",
            plantProbability = "0.81",
            plantDescription = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia," +
                               "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum" +
                               "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium" +
                               "optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis" +
                               "obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam" +
                               "nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,",
            isPlant = false
        ))
}