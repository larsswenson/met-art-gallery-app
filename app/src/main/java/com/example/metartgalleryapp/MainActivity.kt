package com.example.metartgalleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import com.example.metartgalleryapp.ui.theme.MetArtGalleryAppTheme

class MainActivity : ComponentActivity() {
    private val artworks = listOf(
        Artwork(R.drawable.abdul, "Llama", "C. Abdul", "1881"),
        Artwork(R.drawable.jung, "Der Litterat", "Moriz Jung", "1911"),
        Artwork(R.drawable.kalmsteiner, "Kasperltheater", "Hans Kalmsteiner", "1907"),
        Artwork(R.drawable.kinney, "Jocular Ocular", "B. Kinney", "1889"),
        Artwork(R.drawable.lautrec, "Reine de Joie", "Henri de Toulouse-Lautrec", "1892"),
        Artwork(R.drawable.lendecke, "Mode", "Otto Friedr. Carl Lendecke", "1912"),
        Artwork(R.drawable.moller, "Kingitorssuak", "Lars Moller", "1863"),
        Artwork(R.drawable.schiele, "Portrait of a Woman", "Egon Schiele", "1910"),
        Artwork(R.drawable.stephens, "Gallows Bird", "Henry Louis Stephens", "1851")
    )
    private var currentIndex by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetArtGalleryAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtGallery(
                        artworks = artworks,
                        currentIndex = currentIndex,
                        onPreviousClick = { if (currentIndex > 0) currentIndex-- },
                        onNextClick = { if (currentIndex < artworks.size - 1) currentIndex++ },
                        onSearch = { query ->
                            val foundIndex = artworks.indexOfFirst { it.artist.contains(query, ignoreCase = true) }
                            if (foundIndex != -1) {
                                currentIndex = foundIndex
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ArtGallery(
    artworks: List<Artwork>,
    currentIndex: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onSearch: (String) -> Unit
) {
    val artwork = artworks[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = artwork.imageResId),
            contentDescription = artwork.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "${artwork.title}\n${artwork.artist} (${artwork.year})",
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onPreviousClick) {
                Text("Previous")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onNextClick) {
                Text("Next")
            }
        }
        var searchQuery by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "Search",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearch(it)
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = Modifier.weight(1f),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text("Search", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
        }
    }
}

data class Artwork(val imageResId: Int, val title: String, val artist: String, val year: String)

@Preview(showBackground = true)
@Composable
fun ArtGalleryPreview() {
    MetArtGalleryAppTheme {
        ArtGallery(
            artworks = listOf(
                Artwork(R.drawable.abdul, "Llama", "C. Abdul", "1881"),
                Artwork(R.drawable.jung, "Der Litterat", "Moriz Jung", "1911"),
                Artwork(R.drawable.kalmsteiner, "Kasperltheater", "Hans Kalmsteiner", "1907"),
                Artwork(R.drawable.kinney, "Jocular Ocular", "B. Kinney", "1889"),
                Artwork(R.drawable.lautrec, "Reine de Joie", "Henri de Toulouse-Lautrec", "1892"),
                Artwork(R.drawable.lendecke, "Mode", "Otto Friedr. Carl Lendecke", "1912"),
                Artwork(R.drawable.moller, "Kingitorssuak", "Lars Moller", "1863"),
                Artwork(R.drawable.schiele, "Portrait of a Woman", "Egon Schiele", "1910"),
                Artwork(R.drawable.stephens, "Gallows Bird", "Henry Louis Stephens", "1851")
            ),
            currentIndex = 0,
            onPreviousClick = {},
            onNextClick = {},
            onSearch = {}
        )
    }
}



