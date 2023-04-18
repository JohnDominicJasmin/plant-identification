package com.example.plantidentification.feature_plant_identification.presentation.plant_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.plantidentification.R
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.MainState
import java.util.*


@Composable
fun PlantInformation(state: MainState) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background) {


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp),){

            item {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = state.plantName.uppercase(Locale.ROOT),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Visible,
                    color = Color.Black,
                    textAlign = TextAlign.Center)


                AsyncImage(
                    modifier = Modifier.clip(shape = RoundedCornerShape(10.dp)),
                    model = state.plantImageUrl,
                    contentDescription = "Plant Image",
                    placeholder = painterResource(id = R.drawable.placeholder),
                    )



                Text(
                    modifier = Modifier.padding(all = 15.dp),
                    text = state.plantDescription,
                    style = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        lineHeight = 23.sp
                    ), overflow = TextOverflow.Clip)



                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.padding(all = 4.dp), shape = RoundedCornerShape(30),
                    border = BorderStroke(1.dp, Color.Black)) {
                    Text(
                        text = "Back",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 16.dp))
                }
            }


        }


    }

}

@Preview
@Composable
fun PreviewPlantDetails() {
    PlantInformation(state = MainState(
        plantName = "blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium",
        plantImageUrl = "https://cdn.eyeem.com/thumb/4c6754110370bb1b6faf95d6e1d888ca9c3a9cc8-1570026251491/640/480",
        plantDescription = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia," +
                           "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum" +
                           "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium" +
                           "optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis" +
                           "obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam" +
                           "nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,"
    ))
}