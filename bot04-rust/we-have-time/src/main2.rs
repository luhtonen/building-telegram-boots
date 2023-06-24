use chrono::Utc;

// to run execute following command:
// cargo run --bin main2
fn main() {
    let utc = Utc::now();
    println!("UTC: {}", utc);
}
