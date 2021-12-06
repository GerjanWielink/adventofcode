lines = File.readlines("input_5").map{|l| l.split(" -> ").map{|side| side.split(",").map(&:to_i)}}

bounds = [
  lines.map{|l| [l[0][0], l[1][0]]}.reduce(&:+).max,
  lines.map{|l| [l[0][1], l[1][1]]}.reduce(&:+).max,
]

def distance((x1, y1), (x2, y2))
  Math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2)
end

def on_line_simple?(p, (p1, p2))
  (distance(p1, p2) - (distance(p1, p) + distance(p2, p))).abs < 0.00001
end

b = (0..bounds[0]).map do |x|
  (0..bounds[1]).map do |y|
    lines.filter{|l| on_line_simple?([x, y], l)}.length
  end
end

puts b.reduce(&:+).filter{|x| x >= 2}.length



